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
### Important classes and files
* [Activity.class](https://github.com/hardikSinghBehl/entity-activity-automator/blob/main/src/main/java/com/behl/freezo/entity/embeddable/Activity.java)
* [Patient.class](https://github.com/hardikSinghBehl/entity-activity-automator/blob/main/src/main/java/com/behl/freezo/entity/Patient.java)
* [LoggedInDoctorDetailProvider.class](https://github.com/hardikSinghBehl/entity-activity-automator/blob/main/src/main/java/com/behl/freezo/configuration/security/LoggedInDoctorDetailProvider.java)
* [LoggedInDoctorDetailStorageFilter.class](https://github.com/hardikSinghBehl/entity-activity-automator/blob/main/src/main/java/com/behl/freezo/configuration/security/filter/LoggedInDoctorDetailStorageFilter.java)
* [SecurityConfiguration.class](https://github.com/hardikSinghBehl/entity-activity-automator/blob/main/src/main/java/com/behl/freezo/configuration/security/SecurityConfiguration.java)
* [Migration .sql file](https://github.com/hardikSinghBehl/entity-activity-automator/blob/main/src/main/resources/db/migration/V001__creating_tables.sql)
---
### Explanation
* #### Problem Statement
For every entity for which CRUD operation is performed, there are some columns that are repeated for all of them (created_at, created_by etc are used in this POC).
In every POST and PUT operation the developer has to expicility provide values in the columns.

* #### Goal
**EXAMPLE** : If a table contains 5 columns, `full_name` and the 4 activity columns (mentioned above), the code in the service layer would look like the one below:
```
var user = new User();
user.setFullName("Whatever came from frontend");
user.setCreatedAt(current-time);
user.setCreatedBy(id-from-decoding-JWT);
user.setUpdatedAt(current-time);
user.setUpdatedBy(id-from-decoding-JWT);

db.save(user);
```
The **goal** of this POC is to convert the above code to the one below, with the values in the remaining column being filled automatically:
```
var user = new User();
user.setFullName("Whatever came from frontend");
db.save(user);
```
while the migration scipt remains the same 😄
```
CREATE TABLE users (
  id UUID PRIMARY KEY DEFAULT UUID(),
  full_name VARCHAR(100) NOT NULL,
  created_at TIMESTAMP NOT NULL,
  created_by UUID NOT NULL, 
  updated_at TIMESTAMP NOT NULL,
  updated_by UUID NOT NULL
);
```
* #### Solution
1. Create a class `Activity` and annotate it with `@Embeddable`, define the columns that are to be kept common in the required entities. Using @PrePersist and @PreUpdate we'll take care of putting the values in the 4 fields.

```
@Embeddable
@Data
public class Activity {

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @Column(name = "created_by", nullable = false, updatable = false)
    private UUID createdBy;
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
    @Column(name = "updated_by", nullable = false)
    private UUID updatedBy;
}
```
2. Using `@Embedded` annotation, define an instance of the above created class in the Entity classes
```
@Entity
@Table(name = "patients")
@Data
public class Patient implements Serializable {

    private static final long serialVersionUID = 7906541761495255102L;

    @Id
    private UUID id;

    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @Embedded
    @Setter(AccessLevel.NONE)
    private Activity activity = new Activity();

}
```
3. Taking care of `created_at` and `updated_at` (timesatmp fields) using JPA's `@PrePersist` and `@PreUpdate` inside the Activity class created in the first step
```
@PrePersist
void onCreate() {
    this.createdAt = LocalDateTime.now(ZoneId.of("+00:00"));
    this.updatedAt = LocalDateTime.now(ZoneId.of("+00:00"));
}

@PreUpdate
void onUpdate() {
    this.updatedAt = LocalDateTime.now(ZoneId.of("+00:00"));
}
```
4. Defining a class with an instance of [ThreadLocal.class](https://www.baeldung.com/java-threadlocal) to store primary-id of logged-in user with `Getters` and `Setters`
```
public class LoggedInDoctorDetailProvider {

    private static final ThreadLocal<UUID> userId = new ThreadLocal<UUID>();

    public static void setUserId(final UUID id) {
        userId.set(id);
    }

    public static UUID getId() {
        return userId.get();
    }

}
```
5. Creating a filter class extending OncePerRequestFilter.class, to intercept the details of logged-in user who's already authenticated in the [JwtAuthenticationFilter.java](https://github.com/hardikSinghBehl/entity-activity-automator/blob/main/src/main/java/com/behl/freezo/configuration/security/filter/JwtAuthenticationFilter.class)
```
Make sure the created filter executes after the Authentication filter, in this POC it's added in the filter-chain using Spring-security with addAfter()
```
The primary-id (UUID in this case) is to be stored in the above created class in the ThreadLocal instance, so that only that particular thread can access it (Thread-per-request model is used in a REST-API project) using the defined setter method

```
if (authentication != null) {
    final String authorizationHeader = request.getHeader("Authorization");
    final String token = authorizationHeader.substring(7);
    final UUID userId = jwtUtils.extractDoctorId(token);

    LoggedInDoctorDetailProvider.setUserId(userId);
}
```
6. Taking care of `created_by` and `updated_by` using JPA's `@PrePersist` and `@PreUpdate` inside the Activity class created in the first step, ThreadLocal instance will provide the primary-id of the authenticated/logged-in user
```
@PrePersist
void onCreate() {
    this.createdBy = LoggedInDoctorDetailProvider.getId();
    this.updatedBy = LoggedInDoctorDetailProvider.getId();
}

@PreUpdate
void onUpdate() {
    this.updatedBy = LoggedInDoctorDetailProvider.getId();
}
``` 
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

