DELIMITER $$
DROP PROCEDURE IF EXISTS sp_motion_insert$$

CREATE PROCEDURE sp_motion_insert 
(
	IN p_region_coordinates VARCHAR(50), 
	IN p_number_of_changes INT,
	IN p_timestamp INT,
	IN p_microseconds VARCHAR(50),
    IN p_token INT,
    IN p_path_to_image VARCHAR(100),
    IN p_instance_name VARCHAR(50)
)
BEGIN
INSERT INTO motion 
(
	region_coordinates,
	number_of_changes,
	timestamp,
	microseconds,
	token,
	path_to_image,
	instance_name,
    create_date
)
VALUES
(
	p_region_coordinates, 
	p_number_of_changes,
	p_timestamp,
	p_microseconds,
	p_token,
	p_path_to_image,
	p_instance_name,
    now()
);
END
$$
DELIMITER ;