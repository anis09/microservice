package com.smartcampus.userservice.repository;

import com.smartcampus.userservice.entity.User;
import com.smartcampus.userservice.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByUsername(String username);
    
    Optional<User> findByEmail(String email);
    
    Optional<User> findByStudentId(String studentId);
    
    Optional<User> findByEmployeeId(String employeeId);
    
    List<User> findByRole(UserRole role);
    
    List<User> findByDepartment(String department);
    
    List<User> findByRoleAndDepartment(UserRole role, String department);
    
    List<User> findByIsActive(Boolean isActive);
    
    @Query("SELECT u FROM User u WHERE u.role = :role AND u.isActive = true")
    List<User> findActiveUsersByRole(@Param("role") UserRole role);
    
    @Query("SELECT u FROM User u WHERE u.department = :department AND u.isActive = true")
    List<User> findActiveUsersByDepartment(@Param("department") String department);
    
    @Query("SELECT u FROM User u WHERE " +
           "(u.firstName LIKE %:searchTerm% OR u.lastName LIKE %:searchTerm% OR " +
           "u.username LIKE %:searchTerm% OR u.email LIKE %:searchTerm%)")
    List<User> searchUsers(@Param("searchTerm") String searchTerm);
    
    boolean existsByUsername(String username);
    
    boolean existsByEmail(String email);
    
    boolean existsByStudentId(String studentId);
    
    boolean existsByEmployeeId(String employeeId);
}