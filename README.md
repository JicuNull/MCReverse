# MCReverse
 Reverse engineered library for the **german** McDonald's API in Java 

## Used by McCoupon
Please feel free to check out and use **[McCoupon.deals](https://www.mccoupon.deals)** the next time you 
visit McDonald's to get access to basically unlimited amounts of coupons without using the app :)

### Gradle
```java
allprojects {
    repositories {
	...
        maven { url 'https://jitpack.io' }
    }
}
```
```java
dependencies {
    implementation 'com.github.JicuNull:MCReverse:main-SNAPSHOT'
}
```
Find more options here: **[Jitpack](https://jitpack.io/#JicuNull/MCReverse)**

## Connect and login
`McClient` connects to the McDonald's backend server and is used for all interactions with the server.

```java
McClient client = new McClient();
```
You can log in with an existing account by calling `login(email, password)`, where the default deviceId (75408e58622a88c6) is used.

**NOTE** A deviceId must first be confirmed before a login with it is successful.
```java
McClient client = new McClient();
client.login("test@example.org", "123456");
```
or using a custom deviceId
```java
McClient client = new McClient();
client.login("test@example.org", "123456", "deviceId");
```

## Legal
This code is in no way affiliated with, authorized, maintained, sponsored or endorsed by McDonald’s or any of its affiliates or subsidiaries. This is an independent and unofficial software. Use at your own risk.
