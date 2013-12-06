from app import app
from bae.core.wsgi import WSGIApplication
application = WSGIApplication(app)

