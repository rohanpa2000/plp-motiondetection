DELIMITER $$
DROP PROCEDURE IF EXISTS sp_select_last_incidents_image$$

CREATE PROCEDURE sp_select_last_incidents_image 
(
)
BEGIN
(select 
  			region_coordinates,
			number_of_changes,
			timestamp,
			microseconds,
			token,
			path_to_image,
			instance_name,
            create_date
  from motion 
  where instance_name = 'Court1'
  order by create_date desc
  LIMIT 20
)
  UNION ALL
  (select 
  			region_coordinates,
			number_of_changes,
			timestamp,
			microseconds,
			token,
			path_to_image,
			instance_name,
            create_date
  from motion 
  where instance_name = 'Court2'
  order by create_date desc
  LIMIT 20
)
;
END
$$
DELIMITER ;