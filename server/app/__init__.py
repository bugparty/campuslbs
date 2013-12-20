from flask import Flask
from werkzeug import Local, LocalProxy

import os
if 'SERVER_SOFTWARE' in os.environ:
    from app.conf import baidu as conf
    from bae.api import logging
else:
    from app.conf import mysql as conf
    import logging

    
app = Flask(__name__)
app.debug = True
app.config.from_object(conf)
l = Local()
baelogger = logging.getLogger(__name__)
from models.database import db_session, Base

l.session = db_session

if 'SERVER_SOFTWARE' in os.environ:
    from app import views
   
else:
    from app.views.api import *
    



