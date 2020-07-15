DELIMITER |

CREATE EVENT IF NOT EXISTS schedule_due_dates_event
ON SCHEDULE EVERY 1 DAY STARTS '2020-05-07 01:00:00'
ON COMPLETION PRESERVE ENABLE
DO BEGIN
	DECLARE n INT DEFAULT 0;
	DECLARE i INT DEFAULT 0;
	SELECT COUNT(*) FROM due_date_scheduler INTO n;
    SET i=0;
    WHILE i<n DO
		select id, form_name, from_date, to_date, due_date_of_filing, revised_due_date_of_filing, to_be_delete
		into @id, @form_name, @from_date, @to_date, @due_date_of_filing, @revised_due_date_of_filing, @to_be_delete
		from due_date_scheduler LIMIT i, 1;
			IF @to_be_delete = 0 THEN
				select now() into @now;
                IF @revised_due_date_of_filing = NULL THEN
					IF @now > @due_date_of_filing THEN
						update due_date_scheduler
                        set to_be_delete = 1 where id = @id;
					ELSEIF @now >= @to_date AND @now < @due_date_of_filing THEN
						CALL insert_new_due_dates(@form_name, @from_date, @to_date, @due_date_of_filing);
                    END IF;
				ELSE
					IF @now > @revised_due_date_of_filing THEN
						update due_date_scheduler
                        set to_be_delete = 1 where id = @id;
					ELSEIF @now >= @to_date AND @now < @revised_due_date_of_filing THEN
						CALL insert_new_due_dates(@form_name, @from_date, @to_date, @due_date_of_filing);
                    END IF;
                END IF;
            END IF;
        SET i = i + 1;
    END WHILE;
END |

DELIMITER ;