## user login with postman
1. start backend (in Intellij)
2. start a mongo db e.g. docker container e.g. on port 27017.
3. create a database "biokisteDB" with a collection "appusers".
4. Add user as a new document like 
```
{
  "username": "testuser",
  "password": "$argon2id$v=19$m=4096,t=3,p=1$rT9pe1+RlXrtKpnXAGkjJQ$XRvz6fhzpM+kD4ERh1joXM5Y8/wXk5GO2Q+wfxmpiRg"
}
```
