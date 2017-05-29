# cataloguer
This desktop app is designed to store audio, video, documents and books in remote database, also it provides facilities to browse, search and manupulate data in catalog according users role.
# Available roles
- admin - can browse, add, delete file from catalog without limits.
- default - can browse and add files, but with limits.
- guest - can only browse and suggest new files to catalog.

# Email service
If you just a guest you could suggest resources througth the email message, which will contain resources, that you suggest.

# Additional features
- storing data in remote database
- auxiliary concurrency to improve perfomance
- using logger for quick bug fix

# Launching app
after compiling target classes using maven open folder "target" in console and input:

"java -jar cataloguer-1.0.1-jar-with-dependencies.jar"
