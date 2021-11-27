## Automating Entity Activity-fields in a Java Spring-boot project
#### <ins>Activity-fields</ins> in this context are being used to refer to fields such as `created_at`, `created_by`, `updated_at` and `updated_by` that are common in any entity for which CRUD APis are exposed
#### Approach Implemented using
* JPA's
  * @Embedded
  * @Embeddable 
  * @PrePersist
  * @PreUpdate
* ThreadLocal.class
* Http Request Filters (OncePerRequestFilter.class)
---

#### Repository used in [Medium Article](https://medium.com/@behl-hardik/automating-entity-activity-fields-using-threadlocal-and-jpas-embeddable-and-embedded-5b46d0a497c2)
#### [Deployed Application Link](https://entity-activity-automator.herokuapp.com/swagger-ui.html) 🖥🥸
#### can ⭐️ Repository if found helpful or leave comments in [Discussions](https://github.com/hardikSinghBehl/entity-activity-automator/discussions) 💬😄

---
### Explanation
* #### Recording Demonstration
https://user-images.githubusercontent.com/69693621/143672072-c97f3b59-fbec-45f5-8199-a8c96fa62ed5.mov


---
### Local Setup Guide
* Install Java 17 (recommended to use [SdkMan](https://sdkman.io))
```
sdk install java 17-open
```
* Install Maven (recommended to use [SdkMan](https://sdkman.io))
```
sdk install maven
```

* Clone the repo and run the below command in the core
```
git clone https://github.com/hardikSinghBehl/entity-activity-automator.git
```
```
mvn clean install
```

* To start the application, run any of the below 2 commands in the core
```
mvn spring-boot:run &
```

```
java -jar target/entity-activity-embeder-0.0.1-SNAPSHOT.jar &
```
* Access the swagger-ui
```
http://localhost:8080/swagger-ui.html
```


#### References
* [Baeldungs Article on @Embedded and @Embeddable](https://www.baeldung.com/jpa-embedded-embeddable)
* [Baeldungs Article on ThreadLocal.class](https://www.baeldung.com/java-threadlocal)
* [JPA Entity life cycle events](https://www.baeldung.com/jpa-entity-lifecycle-events)
* [OncePerRequestFilter.class](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/filter/OncePerRequestFilter.html)
* Spring Security Filter chain
  * [Video Explanation](https://www.youtube.com/watch?v=EeXFwR21J1A)
  * [Adding custom filters](https://www.baeldung.com/spring-security-custom-filter)

