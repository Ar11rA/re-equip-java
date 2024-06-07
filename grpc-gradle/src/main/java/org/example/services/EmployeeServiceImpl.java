package org.example.services;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.example.domain.Employee;
import org.example.employee.*;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public void getEmployeeByRole(GetEmployeeByRoleRequest request, StreamObserver<GetEmployeeListResponse> responseObserver) {
        List<GetEmployeeResponse> responses = new ArrayList<>();
        String hql = "FROM Employee E WHERE E.role = :employee_role";
        Query<Employee> query = session.createQuery(hql, Employee.class);
        query.setParameter("employee_role", request.getRole());
        List<Employee> employees = query.list();
        employees.forEach(employee -> {
            responses.add(GetEmployeeResponse.newBuilder()
                    .setId(employee.getId())
                    .setName(employee.getName())
                    .setRole(employee.getRole())
                    .build()
            );
        });
        GetEmployeeListResponse response = GetEmployeeListResponse.newBuilder()
                .addAllList(responses)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
