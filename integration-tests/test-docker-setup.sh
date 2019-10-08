echo "Starting Tests of Top Spin for docker setup"
echo "Building docker setup"
version=$1
cd ..
mvn clean install -Ddocker
if [ $? -eq 0 ]; then
    echo "Build completed. Starting tests"
    cd integration-tests
    echo "Starting discovery service"
    java -jar ../discovery-service/target/discovery-service-${version}.jar
    if [ $? -eq 0 ]; then
      echo "Starting config service"
      java -jar ../config-service/target/config-service-${version}.jar
      if [ $? -eq 0 ]; then
          echo "starting Auth service"
          java -jar ../auth-service/target/auth-service-${version}.jar &
          mvn clean install
      else
          echo "Failed to start config service. check log. Tests failed"
      fi

    else
      echo "Failed to start Discovery service. check log. Tests failed"
    fi

else
    echo "Build failed. Kindly check the logs"
fi