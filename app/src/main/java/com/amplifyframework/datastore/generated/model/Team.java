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

/** This is an auto generated class representing the Team type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Teams")
public final class Team implements Model {
  public static final QueryField ID = field("Team", "id");
  public static final QueryField NAME = field("Team", "name");
  public static final QueryField TASK_TEAMS_ID = field("Team", "taskTeamsId");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String") String name;
  private final @ModelField(targetType="ID") String taskTeamsId;
  public String getId() {
      return id;
  }
  
  public String getName() {
      return name;
  }
  
  public String getTaskTeamsId() {
      return taskTeamsId;
  }
  
  private Team(String id, String name, String taskTeamsId) {
    this.id = id;
    this.name = name;
    this.taskTeamsId = taskTeamsId;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Team team = (Team) obj;
      return ObjectsCompat.equals(getId(), team.getId()) &&
              ObjectsCompat.equals(getName(), team.getName()) &&
              ObjectsCompat.equals(getTaskTeamsId(), team.getTaskTeamsId());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getName())
      .append(getTaskTeamsId())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Team {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("name=" + String.valueOf(getName()) + ", ")
      .append("taskTeamsId=" + String.valueOf(getTaskTeamsId()))
      .append("}")
      .toString();
  }
  
  public static BuildStep builder() {
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
  public static Team justId(String id) {
    return new Team(
      id,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      name,
      taskTeamsId);
  }
  public interface BuildStep {
    Team build();
    BuildStep id(String id);
    BuildStep name(String name);
    BuildStep taskTeamsId(String taskTeamsId);
  }
  

  public static class Builder implements BuildStep {
    private String id;
    private String name;
    private String taskTeamsId;
    @Override
     public Team build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Team(
          id,
          name,
          taskTeamsId);
    }
    
    @Override
     public BuildStep name(String name) {
        this.name = name;
        return this;
    }
    
    @Override
     public BuildStep taskTeamsId(String taskTeamsId) {
        this.taskTeamsId = taskTeamsId;
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
    private CopyOfBuilder(String id, String name, String taskTeamsId) {
      super.id(id);
      super.name(name)
        .taskTeamsId(taskTeamsId);
    }
    
    @Override
     public CopyOfBuilder name(String name) {
      return (CopyOfBuilder) super.name(name);
    }
    
    @Override
     public CopyOfBuilder taskTeamsId(String taskTeamsId) {
      return (CopyOfBuilder) super.taskTeamsId(taskTeamsId);
    }
  }
  
}
