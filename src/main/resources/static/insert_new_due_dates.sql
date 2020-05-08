CREATE DEFINER=`root`@`localhost` PROCEDURE `insert_new_due_dates`(IN form_name VARCHAR(50), IN from_date DATE, IN to_date DATE, IN due_date_of_filing DATE)
BEGIN
	DECLARE new_from_date date;
    DECLARE new_to_date date;
    DECLARE new_due_date_of_filing date;
	select periodicity, return_type
    into @periodicity, @return_type from return_forms where form_name = form_name;
    IF @periodicity = "monthly" THEN
		SET new_from_date = date_add(from_date, INTERVAL 1 MONTH);
        SET new_to_date = date_add(to_date, INTERVAL 1 MONTH);
        SET new_due_date_of_filing = date_add(due_date_of_filing, INTERVAL 1 MONTH);
	ELSEIF @periodicity = "yearly" THEN
		SET new_from_date = date_add(from_date, INTERVAL 1 YEAR);
        SET new_to_date = date_add(to_date, INTERVAL 1 YEAR);
        SET new_due_date_of_filing = date_add(due_date_of_filing, INTERVAL 1 YEAR);
	ELSE
		select month(from_date) into @from_date_month;
        IF @from_date_month = 4 OR @from_date_month = 7 OR @from_date_month = 1 THEN
			SET new_from_date = date_add(from_date, INTERVAL 3 MONTH);
			SET new_to_date = date_add(to_date, INTERVAL 3 MONTH);
			SET new_due_date_of_filing = date_add(due_date_of_filing, INTERVAL 3 MONTH);
		ELSE
			SET new_from_date = date_add(from_date, INTERVAL 3 MONTH);
			SET new_to_date = date_add(to_date, INTERVAL 3 MONTH);
            IF @return_type = "tds" THEN
				SET new_due_date_of_filing = date_add(due_date_of_filing, INTERVAL 4 MONTH);
			ELSE
				SET new_due_date_of_filing = date_add(due_date_of_filing, INTERVAL 3 MONTH);
            END IF;
        END IF;
    END IF;
    insert into due_date_scheduler (form_name, from_date, to_date, due_date_of_filing)
    VALUES (form_name, new_from_date, new_to_date, new_due_date_of_filing);
END