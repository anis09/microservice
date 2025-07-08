package com.smartcampus.userservice.service;

import com.smartcampus.userservice.dto.UserDto;
import com.smartcampus.userservice.entity.User;
import com.smartcampus.userservice.entity.UserRole;
import com.smartcampus.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    public Page<UserDto> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(this::convertToDto);
    }
    
    public Optional<UserDto> getUserById(Long id) {
        return userRepository.findById(id)
                .map(this::convertToDto);
    }
    
    public Optional<UserDto> getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(this::convertToDto);
    }
    
    public Optional<UserDto> getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(this::convertToDto);
    }
    
    public Optional<UserDto> getUserByStudentId(String studentId) {
        return userRepository.findByStudentId(studentId)
                .map(this::convertToDto);
    }
    
    public List<UserDto> getUsersByRole(UserRole role) {
        return userRepository.findByRole(role).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    public List<UserDto> getUsersByDepartment(String department) {
        return userRepository.findByDepartment(department).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    public List<UserDto> getActiveUsersByRole(UserRole role) {
        return userRepository.findActiveUsersByRole(role).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    public List<UserDto> searchUsers(String searchTerm) {
        return userRepository.searchUsers(searchTerm).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    public UserDto createUser(UserDto userDto) {
        validateUserCreation(userDto);
        User user = convertToEntity(userDto);
        
        // Generate student/employee ID if needed
        if (user.getRole() == UserRole.STUDENT && user.getStudentId() == null) {
            user.setStudentId(generateStudentId());
        } else if (user.getRole() == UserRole.TEACHER && user.getEmployeeId() == null) {
            user.setEmployeeId(generateEmployeeId());
        }
        
        user.setCreatedAt(LocalDateTime.now());
        User savedUser = userRepository.save(user);
        return convertToDto(savedUser);
    }
    
    public UserDto updateUser(Long id, UserDto userDto) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    updateUserFields(existingUser, userDto);
                    existingUser.setUpdatedAt(LocalDateTime.now());
                    User updatedUser = userRepository.save(existingUser);
                    return convertToDto(updatedUser);
                })
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }
    
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }
    
    public UserDto deactivateUser(Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setIsActive(false);
                    user.setUpdatedAt(LocalDateTime.now());
                    User updatedUser = userRepository.save(user);
                    return convertToDto(updatedUser);
                })
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }
    
    public UserDto activateUser(Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setIsActive(true);
                    user.setUpdatedAt(LocalDateTime.now());
                    User updatedUser = userRepository.save(user);
                    return convertToDto(updatedUser);
                })
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }
    
    private void validateUserCreation(UserDto userDto) {
        if (userRepository.existsByUsername(userDto.getUsername())) {
            throw new RuntimeException("Username already exists: " + userDto.getUsername());
        }
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new RuntimeException("Email already exists: " + userDto.getEmail());
        }
        if (userDto.getStudentId() != null && userRepository.existsByStudentId(userDto.getStudentId())) {
            throw new RuntimeException("Student ID already exists: " + userDto.getStudentId());
        }
        if (userDto.getEmployeeId() != null && userRepository.existsByEmployeeId(userDto.getEmployeeId())) {
            throw new RuntimeException("Employee ID already exists: " + userDto.getEmployeeId());
        }
    }
    
    private String generateStudentId() {
        // Simple student ID generation logic
        long count = userRepository.findByRole(UserRole.STUDENT).size();
        return "STU" + String.format("%06d", count + 1);
    }
    
    private String generateEmployeeId() {
        // Simple employee ID generation logic
        long count = userRepository.findByRole(UserRole.TEACHER).size() + userRepository.findByRole(UserRole.ADMIN).size();
        return "EMP" + String.format("%06d", count + 1);
    }
    
    private void updateUserFields(User existingUser, UserDto userDto) {
        if (userDto.getEmail() != null) existingUser.setEmail(userDto.getEmail());
        if (userDto.getFirstName() != null) existingUser.setFirstName(userDto.getFirstName());
        if (userDto.getLastName() != null) existingUser.setLastName(userDto.getLastName());
        if (userDto.getPhoneNumber() != null) existingUser.setPhoneNumber(userDto.getPhoneNumber());
        if (userDto.getDepartment() != null) existingUser.setDepartment(userDto.getDepartment());
        if (userDto.getYearOfStudy() != null) existingUser.setYearOfStudy(userDto.getYearOfStudy());
        if (userDto.getDateOfBirth() != null) existingUser.setDateOfBirth(userDto.getDateOfBirth());
        if (userDto.getAddress() != null) existingUser.setAddress(userDto.getAddress());
        if (userDto.getIsActive() != null) existingUser.setIsActive(userDto.getIsActive());
    }
    
    private UserDto convertToDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setRole(user.getRole());
        dto.setStudentId(user.getStudentId());
        dto.setEmployeeId(user.getEmployeeId());
        dto.setDepartment(user.getDepartment());
        dto.setYearOfStudy(user.getYearOfStudy());
        dto.setDateOfBirth(user.getDateOfBirth());
        dto.setAddress(user.getAddress());
        dto.setIsActive(user.getIsActive());
        return dto;
    }
    
    private User convertToEntity(UserDto dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setRole(dto.getRole());
        user.setStudentId(dto.getStudentId());
        user.setEmployeeId(dto.getEmployeeId());
        user.setDepartment(dto.getDepartment());
        user.setYearOfStudy(dto.getYearOfStudy());
        user.setDateOfBirth(dto.getDateOfBirth());
        user.setAddress(dto.getAddress());
        user.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : true);
        return user;
    }
}