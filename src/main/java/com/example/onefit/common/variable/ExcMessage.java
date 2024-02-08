
package com.example.onefit.common.variable;

public interface ExcMessage {

    String USER_NOT_FOUND = "User not found";
    String SUCCESSFULLY_VERIFICATION = "Successfully verification";
    String INCORRECT_EMAIL_VER = "Incorrect email verification";
    String PASSWORD_INCORRECT = "Password incorrect";
    String TIME_OUT = "Time out";
    String BAD_CREDENTIALS = "bad credentials";
    String REDIS_EXCEPTION = "RedisConnectionFailureException";
    String COURSE_NOTFOUND = "Course is not found id %s";
    String ENTITY_NOTFOUND = "Entity not found";
    String USERNAME_NOTFOUND = "Username not found:";
    String EMAIL_NOTFOUND = "Email with email: %s is not found";
    String FACILITIES_NOTFOUND = "Facilities is not found id %s";
    String LOCATION_NOTFOUND = "Location with id %s not found";
    String CATEGORY_NOTFOUND = "Category with id %s not found";
    String USER_ID_NOTFOUND = "User with id: %s not found";
    String ROLE_ID_NOTFOUND = "Role with id: %s not found";
    String PERMISSION_ID_NOTFOUND = "Permission with id: %s not found";
    String ACTIVITY_ID_NOTFOUND = "Activity with id: %s not found";
    String STUDIO_ID_NOTFOUND = "Studio with id: %s not found";
    String NOT_CORRECT = "Username or password is not correct";
    String SUBSCRIPTION_NOTFOUND = "Subscription is not found id %s";
    String SUBSCRIPTION_NOTFOUND_WITH_USER = "Subscription is not found id %s";
    String STUDIO_NOTFOUND = "Studio is not found id: %s";
    String NOT_ALLOWED_FEMALE = "The course is only for men";
    String NOT_ALLOWED_MALE = "The course is only for woman";
    String ACCOUNT_NOT_VERIFIED = "Account via unverified email";

}
