# Resource-bundle
Resource bundle is a spring boot micro-service, contains much of what we need for developing a multilingual application.
The features we’ve covered make manipulation of different locales pretty straightforward. 
We also avoid hardcoded values, allowing us to expand the supported Locales by simply adding new Locale records allowing our application 
to be smoothly modified and  maintained. The Resource bundle makes use of Redis as it's in-memory cache, which enables it to be super fast.
It also uses our own infra which contains several base classes and utils.


