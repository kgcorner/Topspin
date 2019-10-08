version=$1
echo "Starting Tests of Top Spin for multi-module setup for version ${version}"
echo "Building multi-module setup"

mvn clean install -DmultiModule=true -DskipTests
if [ $? -eq 0 ]; then
    echo "Build completed. Starting tests"
    echo "Starting discovery service"
    echo "Command java -jar packaging/executable-jar-packaging/spring-discovery-service/target/spring-discovery-service-${version}.jar --server.port=8761 &"
    java11 -jar packaging/executable-jar-packaging/spring-discovery-service/target/spring-discovery-service-${version}.jar --server.port=8761 &
    echo "Waiting for 20 sec"
    sleep 20
    if [ $? -eq 0 ]; then
      echo "Starting config service"
      echo "Command java -jar packaging/executable-jar-packaging/spring-config-service/target/spring-config-service-${version}.jar --server.port=8888 &"
      java11 -jar packaging/executable-jar-packaging/spring-config-service/target/spring-config-service-${version}.jar --server.port=8888 &
      echo "Waiting for 20 sec"
      sleep 20

      if [ $? -eq 0 ]; then
          echo "starting Auth service"
          java11 -jar packaging/executable-jar-packaging/spring-auth-service/target/spring-auth-service-${version}.jar  --server.port=9001 &
          echo "Waiting for 20 sec"
          sleep 20
          mvn -f integration-tests/pom.xml clean install
          kill %3
          kill %2
          kill %1

      else
          echo "Failed to start config service. check log. Tests failed"
      fi

    else
      echo "Failed to start Discovery service. check log. Tests failed"
    fi

else
    echo "Build failed. Kindly check the logs"
fi