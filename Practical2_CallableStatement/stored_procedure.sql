-- Create stored procedure to get employee city by ID

DELIMITER //

CREATE PROCEDURE GetEmployeeCity(
    IN emp_id INT,
    OUT emp_city VARCHAR(50)
)
BEGIN
    SELECT city INTO emp_city
    FROM emp
    WHERE id = emp_id;
END //

DELIMITER ;

-- Test the stored procedure
-- CALL GetEmployeeCity(101, @city);
-- SELECT @city;
