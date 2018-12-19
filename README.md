# FileDownloader

File Downloader that can download data from multiple sources and protocols to local disk.

###Description

The project downloads files using protocols(http, https, ftp, sftp) to a configurable location.   
This is structured such that, it can be easily extended to support another protocols.


This project solely does not provide and User Interface as only Http API's has been exposed, 
[Api Docs](https://documenter.getpostman.com/view/437815/Rzn6v2zk).

To get an User Interface this project can be clubbed with a 
[React App](https://github.com/gaurav0107/filedownloaderUi-react) which has been designed to use API's exposed by This project.

These two has been kept separate to not enforce use of any specific User Interface.


## Getting Started

#### Prerequisites

For building and running the application you need:

- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven 3](https://maven.apache.org)

#### Installation

Clone the repository:

  ```shell
  git clone git://github.com/gaurav0107/FileDownloader.git
  cd FileDownloader
  ```

If this is your first time using Github, review http://help.github.com to learn the basics.

#### Run Tests:

Complete Tests

```shell
mvn test
```

#### Run Application

Using Spring Boot:
```shell
mvn spring-boot:run
```

## Api Docs
[Postman Docs](https://documenter.getpostman.com/view/437815/Rzn6v2zk)



