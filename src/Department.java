/**
 * Department interface for different types of department in Emergency Department
 * 
 * @author Dong Hyeog Jang
 * @date   11 March 2024
 * 
 */
public interface Department {
    void enterDepartment(Patient patient);
    void leaveDepartment(Patient patient);
}
