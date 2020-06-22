-- Find duplicate aat_subjects
SELECT aat_uid, COUNT(*) as total FROM `aat_subjects` GROUP BY aat_uid HAVING total > 1 ORDER BY total DESC

DELETE FROM `subject_terms` WHERE aat_uid ='' and mapping_id = 73

SELECT * FROM `subject_terms` WHERE native_term = 'ohrazení/fortifikace nesp.' and mapping_id = 73

-- Fix earch_temporal
UPDATE `earch_temporal` SET `end_year`= `start_year`;
UPDATE `earch_temporal` SET `start_year`= `aat_uid`;
UPDATE `earch_temporal` SET `aat_uid`= '';
