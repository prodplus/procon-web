cd proconweb
ng build --prod=true --outputPath=../proconweb-api/src/main/resources/META-INF/resources
cd ..
cd proconweb-api
mvn package -DskipTests