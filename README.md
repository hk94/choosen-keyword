# choosen-keyword
The source code are Initial.java, GenTrapdoor.java, keywordTest.java, libforclient.java, libforserver.java
We assume Alice play the role of server, Bob play the role of client.
First, Bob run Initial.java to get publickey and privatekey saved in test/
For Bob, run GenTrapdoor.java, generate some trapdoor saved in test/, send the trapdoors and publickey to Alice.
For Alice, run keywordTest.java, program read trapdoor automatically, input some keyword to check.


©2018 hk94, Matsuura Lab. Some Rights Reserved.
