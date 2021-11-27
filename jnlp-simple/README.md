- Package
cd .\out\production\jnlp-simple
jar cf TestJnlp.jar *
copy TestJnlp.jar to jnlp-simple  

- Debug
java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 -cp ./*  com.company.TestJnlp


https://mkyong.com/Java/java-web-start-jnlp-tutorial-unofficial-guide/