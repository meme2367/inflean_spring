package jpabook.jpashop.domain;


import javax.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
public class Address {

  private String city;
  private String street;
  private String zipcode;
}
