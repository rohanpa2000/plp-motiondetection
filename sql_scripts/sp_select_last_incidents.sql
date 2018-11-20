DELIMITER $$
DROP PROCEDURE IF EXISTS sp_select_last_incidents$$

CREATE PROCEDURE sp_select_last_incidents 
(
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
            MAX(create_date) 
    FROM motion 
	GROUP BY instance_name
;
END
$$
DELIMITER ;