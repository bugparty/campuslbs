from app import app, db_session,logging
from app.models import User,Group,Authority,Session
import re
from flask import render_template,request
from flask.views import MethodView
from flask import jsonify
import json
import datetime
from sqlalchemy.exc import IntegrityError
from app.apperror import *
app.config['SECRET_KEY'] = 'don`t tell anybody,sdfsdfsdfzssdfsdfewwefwe'




logger = logging.getLogger('app.view.api')
def query_email(email):
    try:
        r = db_session.query(User).filter_by(email =email).all()
    except:
        db_session.rollback()
        r = None
    db_session.close()
    return r

def need_auth(role):
        def _need_auth(func):
            def __need_auth(*args,**kw):
                try:
                    sid = request.args['token']
                except:
                    raise NoTokenException()

                #User.get(sid)
                return func(*args,**kw)
            return __need_auth
        return _need_auth

vemail = re.compile('\w+\@\w+\.\w+')
vusername = re.compile('\w+')
vpassword = re.compile('\w+')

@app.route('/test')
@need_auth('admin')
def hello():
    return 'hello,world'

def jmsg(code,extra=None):
    code = int(code)
    emsgs = ('lack of email address',
   'email address duplicated',
    'user exists')
    msgs = ('email usable',
        'user created',
        'login success',)
    if code > 0:
            msg = msgs[code-1]
    else:
            msg = emsgs[-code -1]
    if not extra:
            return jsonify({'status': int(code),'msg':msg})
    else:
            return jsonify(dict({'status': int(code),'msg':msg},**extra))


def create_user(jdic):
    
    group_user = db_session.query(Group).filter_by(name='user').one()
    time_expire = datetime.timedelta(10)+datetime.datetime.now()
    s = Session(time_expire, jdic['username'],jdic['password'])
    db_session.add(s)
    db_session.commit()
    u = User(jdic['username'],jdic['email'],jdic['password'],group_user)
    u.session = s
    db_session.add(u)
    db_session.commit()
    db_session.close()
    return s.token

class RegisterAPI(MethodView):
    def get(self):
        email = request.args.get('email',None)
        if email == None:
            return jmsg(-1)
        if vemail.match(email):
            if query_email(email):
                    return jmsg(-2)

        return jmsg(1)

    def post(self):
        try:
                r = json.loads(request.data)
        except ValueError, err:
                return 'error during prasing json:%s'% err
        try:
                if not vemail.match(r['email']):
                    return 'email not correct %s'% r['email']
                if not vusername.match(r['username']):
                    return 'username not correct'
                if not vpassword.match(r['password']):
                    return 'password not correct'
        except KeyError, err:
                return 'leaking key %s' % err
        try:
                token = create_user(r)
        except IntegrityError:
                db_session.rollback()
                db_session.close()
                return jmsg(-3)
        
        db_session.close()
        return jmsg(2, extra={'token':token})

class LoginAPI(MethodView):
    def post(self):
        try:
                r = json.loads(request.data)
        except ValueError, err:
                return 'error during prasing json:%s'% err
        try:
                if not vemail.match(r['email']):
                    return 'email not correct %s'% r['email']
                if not vpassword.match(r['password']):
                    return 'password not correct'
        except KeyError, err:
                return 'leaking key %s' % err
        try:
                user = db_session.query(User).filter_by(email=r['email']).one()
        except Exception,e:
                db_session.rollback()
                db_session.close()
                logger.error(str(e))
                return 'unknown error'
        
        if user.password == r['password']:
            token = user.session.token
            db_session.close()
            return jmsg(3,extra={'token':token})
        else:
            return 'password not correct'


register_view = RegisterAPI.as_view('register')
login_view = LoginAPI.as_view('login')
app.add_url_rule('/capi/register', view_func = register_view  )
app.add_url_rule('/capi/login', view_func = login_view  )




