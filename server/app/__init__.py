from flask import Flask
from werkzeug import Local, LocalProxy

import os
if 'SERVER_SOFTWARE' in os.environ:
    from app.conf import baidu as conf
else:
    from app.conf import mysql as conf

    
app = Flask(__name__)
app.debug = True
app.config.from_object(conf)
l = Local()

from models.database import db_session, Base

l.session = db_session


from app import views

