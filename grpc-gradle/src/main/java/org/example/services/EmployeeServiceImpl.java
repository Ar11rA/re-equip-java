package org.example.services;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.example.domain.Employee;
import org.example.employee.*;
import org.hibernate.Session;

public class EmployeeServiceImpl extends EmployeeServiceGrpc.EmployeeServiceImplBase {

    private final Session session;

    public EmployeeServiceImpl(Session session) {
        this.session = session;
    }

    @Override
    public void getEmployee(GetEmployeeRequest request, StreamObserver<GetEmployeeResponse> responseObserver) {
        Employee employee = session.get(Employee.class, request.getId());
        if (employee == null) {
            responseObserver.onError(Status.NOT_FOUND
              .withDescription("Employee with id " + request.getId() + " not found")
              .asRuntimeException());
            return;
        }
        GetEmployeeResponse response = GetEmployeeResponse.newBuilder()
          .setId(employee.getId())
          .setName(employee.getName())
          .setRole(employee.getRole())
          .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void createEmployee(CreateEmployeeRequest request, StreamObserver<CreateEmployeeResponse> responseObserver) {
        Employee employee = new Employee();
        employee.setName(request.getName());
        employee.setRole(request.getRole());
        session.save(employee);
        CreateEmployeeResponse response = CreateEmployeeResponse.newBuilder()
          .setId(employee.getId())
          .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
