package org.example.protobuf;

import org.example.models.Address;
import org.example.models.Job;
import org.example.models.Person;
import org.example.models.Company;
import org.example.models.CompanyType;

public class PersonDemo {
    public static void main(String[] args) {
        Address address = Address.newBuilder()
          .setStreet("123 Main St")
          .setCity("Anytown")
          .build();

        Job job = Job.newBuilder()
          .setTitle("Acme")
          .setSalary(1000000)
          .build();

        Person person = Person.newBuilder()
          .setName("John Doe")
          .setAge(42)
          .setAddress(address)
          .setJob(job)
          .build();

        Company company = Company.newBuilder()
          .putEmployees("ABC", person)
          .setType(CompanyType.PRIVATE)
          .build();

        System.out.println(person);
        System.out.println(company);
    }
}
