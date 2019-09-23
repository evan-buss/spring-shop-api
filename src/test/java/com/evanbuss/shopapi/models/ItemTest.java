package com.evanbuss.shopapi.models;

import org.hibernate.validator.HibernateValidator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.ConstraintViolation;
import java.util.Set;

public class ItemTest {

  private List list;
  private LocalValidatorFactoryBean validator;

  @Before
  public void setUp() throws Exception {
    User user = new User("Evan Buss", "email@google.com", "securepass");
    Family family = new Family("Evan's Family", user);

    list = new List("Grocery List", "Simple List", family);

    validator = new LocalValidatorFactoryBean();
    validator.setProviderClass(HibernateValidator.class);
    validator.afterPropertiesSet();
  }

  @Test
  public void testValidItem() {
    Item item = new Item(list, "Red Bull", "The original");
    Set<ConstraintViolation<Item>> violations = validator.validate(item);
    Assert.assertEquals(violations.size(), 0);
  }

  @Test
  public void testTitleMissingIsInvalid() {
    Item item = new Item(list, "", "The Original");
    Set<ConstraintViolation<Object>> violations = validator.validate(item);
    Assert.assertEquals(violations.size(), 1);
  }
}
