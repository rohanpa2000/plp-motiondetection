DELIMITER $$
CREATE TABLE `motion` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `region_coordinates` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `number_of_changes` int(11) DEFAULT NULL,
  `timestamp` int(11) DEFAULT NULL,
  `microseconds` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `token` int(11) DEFAULT NULL,
  `path_to_image` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `instance_name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `create_date` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `item_id` int(11) DEFAULT NULL,
  `tenant_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=85 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
END$$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE `sp_motion_insert`(
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
END$$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE `sp_motion_insert_tenant`(
	IN p_region_coordinates VARCHAR(50), 
	IN p_number_of_changes INT,
	IN p_timestamp INT,
	IN p_microseconds VARCHAR(50),
    IN p_token INT,
    IN p_path_to_image VARCHAR(100),
    IN p_instance_name VARCHAR(50),
    IN p_tenant_id INT,
    IN p_item_id INT
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
    create_date,
	tenant_id,
    item_id
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
    now(),
    p_tenant_id,
    p_item_id
);
END$$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE `sp_select_last_incidents`(
)
BEGIN
	SELECT 
			region_coordinates,
			number_of_changes,
			timestamp,
			microseconds,
			token,
			path_to_image,
			instance_name,
            MAX(create_date) as create_date
    FROM motion 
	GROUP BY instance_name
;
END$$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE `sp_select_motion_by_timestamperange`(
	IN p_start_timestamp long, 
	IN p_end_timestamp long
)
BEGIN
	SELECT
			region_coordinates,
			number_of_changes,
			timestamp,
			microseconds,
			token,
			path_to_image,
			instance_name,
			create_date,
			tenant_id,
			item_id


    FROM motion
    WHERe 
		timestamp >= p_start_timestamp
        AND
        timestamp <= p_end_timestamp

;
END$$
DELIMITER ;
