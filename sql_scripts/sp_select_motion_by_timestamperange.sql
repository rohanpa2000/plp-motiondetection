DELIMITER $$
DROP PROCEDURE IF EXISTS sp_select_motion_by_timestamperange$$

CREATE PROCEDURE sp_select_motion_by_timestamperange 
(
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
			create_date

    FROM motion
    WHERe 
		timestamp >= p_start_timestamp
        AND
        timestamp <= p_end_timestamp

;
END
$$
DELIMITER ;