mvn install:install-file -Dfile=common-1.0-SNAPSHOT.jar -DgroupId=paas -DartifactId=common -Dversion=1.0-SNAPSHOT -Dpackaging=jar
mvn install:install-file -Dfile=computation-1.0-SNAPSHOT.jar -DgroupId=paas -DartifactId=computation -Dversion=1.0-SNAPSHOT -Dpackaging=jar
mvn install:install-file -Dfile=instance-1.0-SNAPSHOT.jar -DgroupId=paas -DartifactId=instance -Dversion=1.0-SNAPSHOT -Dpackaging=jar
mvn install:install-file -Dfile=qingcloud-sdk-java.jar -DgroupId=com.qingcloud -DartifactId=qingcloud-sdk-java -Dversion=1.2.0 -Dpackaging=jar
mvn install:install-file -Dfile=resource-1.0-SNAPSHOT.jar -DgroupId=paas -DartifactId=resource -Dversion=1.0-SNAPSHOT -Dpackaging=jar