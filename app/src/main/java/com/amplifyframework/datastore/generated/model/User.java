package com.amplifyframework.datastore.generated.model;


import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the User type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Users")
public final class User implements Model {
  public static final QueryField ID = field("User", "id");
  public static final QueryField REGISTERATION_NUMBER = field("User", "registerationNumber");
  public static final QueryField NAME = field("User", "name");
  public static final QueryField AGE = field("User", "age");
  public static final QueryField NUMBER = field("User", "number");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="ID", isRequired = true) String registerationNumber;
  private final @ModelField(targetType="String", isRequired = true) String name;
  private final @ModelField(targetType="Int") Integer age;
  private final @ModelField(targetType="String") String number;
  public String getId() {
      return id;
  }
  
  public String getRegisterationNumber() {
      return registerationNumber;
  }
  
  public String getName() {
      return name;
  }
  
  public Integer getAge() {
      return age;
  }
  
  public String getNumber() {
      return number;
  }
  
  private User(String id, String registerationNumber, String name, Integer age, String number) {
    this.id = id;
    this.registerationNumber = registerationNumber;
    this.name = name;
    this.age = age;
    this.number = number;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      User user = (User) obj;
      return ObjectsCompat.equals(getId(), user.getId()) &&
              ObjectsCompat.equals(getRegisterationNumber(), user.getRegisterationNumber()) &&
              ObjectsCompat.equals(getName(), user.getName()) &&
              ObjectsCompat.equals(getAge(), user.getAge()) &&
              ObjectsCompat.equals(getNumber(), user.getNumber());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getRegisterationNumber())
      .append(getName())
      .append(getAge())
      .append(getNumber())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("User {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("registerationNumber=" + String.valueOf(getRegisterationNumber()) + ", ")
      .append("name=" + String.valueOf(getName()) + ", ")
      .append("age=" + String.valueOf(getAge()) + ", ")
      .append("number=" + String.valueOf(getNumber()))
      .append("}")
      .toString();
  }
  
  public static RegisterationNumberStep builder() {
      return new Builder();
  }
  
  /** 
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   */
  public static User justId(String id) {
    return new User(
      id,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      registerationNumber,
      name,
      age,
      number);
  }
  public interface RegisterationNumberStep {
    NameStep registerationNumber(String registerationNumber);
  }
  

  public interface NameStep {
    BuildStep name(String name);
  }
  

  public interface BuildStep {
    User build();
    BuildStep id(String id);
    BuildStep age(Integer age);
    BuildStep number(String number);
  }
  

  public static class Builder implements RegisterationNumberStep, NameStep, BuildStep {
    private String id;
    private String registerationNumber;
    private String name;
    private Integer age;
    private String number;
    @Override
     public User build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new User(
          id,
          registerationNumber,
          name,
          age,
          number);
    }
    
    @Override
     public NameStep registerationNumber(String registerationNumber) {
        Objects.requireNonNull(registerationNumber);
        this.registerationNumber = registerationNumber;
        return this;
    }
    
    @Override
     public BuildStep name(String name) {
        Objects.requireNonNull(name);
        this.name = name;
        return this;
    }
    
    @Override
     public BuildStep age(Integer age) {
        this.age = age;
        return this;
    }
    
    @Override
     public BuildStep number(String number) {
        this.number = number;
        return this;
    }
    
    /** 
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     */
    public BuildStep id(String id) {
        this.id = id;
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, String registerationNumber, String name, Integer age, String number) {
      super.id(id);
      super.registerationNumber(registerationNumber)
        .name(name)
        .age(age)
        .number(number);
    }
    
    @Override
     public CopyOfBuilder registerationNumber(String registerationNumber) {
      return (CopyOfBuilder) super.registerationNumber(registerationNumber);
    }
    
    @Override
     public CopyOfBuilder name(String name) {
      return (CopyOfBuilder) super.name(name);
    }
    
    @Override
     public CopyOfBuilder age(Integer age) {
      return (CopyOfBuilder) super.age(age);
    }
    
    @Override
     public CopyOfBuilder number(String number) {
      return (CopyOfBuilder) super.number(number);
    }
  }
  
}
