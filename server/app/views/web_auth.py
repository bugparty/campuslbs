#-*- coding:utf-8 -*-
from flask import Flask, g, request,redirect
from flask import render_template
from flask import json
from weibo import APIClient
from bae.core import const
import pymongo
import datetime
from pymongo import MongoClient
from bson.json_util import dumps

APP_KEY = '2200929205'
APP_SECRET = 'd5afe956d4968f66b68fba097e7d8707'

CALLBACK_URL = 'http://campuslbs.duapp.com/weibo_callback'

client = APIClient(app_key=APP_KEY,app_secret=APP_SECRET,redirect_uri=CALLBACK_URL)

url = client.get_authorize_url()

app = Flask(__name__)
app.debug = True

db_name = 'akeJSVesKeAcLtXPQCTn'
@app.route("/mongo")
def mongo_show_collections():
	con = pymongo.Connection(host=const.MONGO_HOST,
	port=int(const.MONGO_PORT))
	db=con[db_name]
	db.authenticate(const.MONGO_USER,const.MONGO_PASS)
	colls = db.collection_names()
	auths = db.weibo_auth
	
	con.disconnect()
	return str(auths.count())

def save_auth(auth_dic):
	con = pymongo.Connection(host=const.MONGO_HOST,
	port=int(const.MONGO_PORT))
	db=con[db_name]
	db.authenticate(const.MONGO_USER,const.MONGO_PASS)
	#colls = db.collection_names()
	auths = db.weibo_auth
	auth_dic.date = datetime.datetime.utcnow()
	post_id = auths.insert(auth_dic)
	con.disconnect()
	return post_id
def read_auth_lasted():
	con = pymongo.Connection(host=const.MONGO_HOST,
	port=int(const.MONGO_PORT))
	db=con[db_name]
	db.authenticate(const.MONGO_USER,const.MONGO_PASS)
	#colls = db.collection_names()
	cusor = db['weibo_auth'].find().sort('date',pymongo.DESCENDING).limit(1)
	result = cusor[0]
	
	con.disconnect()
	
	return result
	
	
	
	
@app.route("/get_auth")
def latest_auth():
	result = read_auth_lasted()
	return dumps(result)
	

		
@app.route("/weibo_callback")
def authize():
	code = request.args['code']
	auth_dic = client.request_access_token(code)
	#client.set_access_token(dic.access_token,dic.expires_in)
	post_id = save_auth(auth_dic)
	return str(post_id)
        
@app.route("/")
def homepage(code = None):
	return redirect(url)




from bae.core.wsgi import WSGIApplication
application = WSGIApplication(app)

