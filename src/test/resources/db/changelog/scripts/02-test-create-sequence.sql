CREATE SEQUENCE VIDEO_METADATA_SEQ START WITH (select max(id) + 1 from video_metadata);
CREATE SEQUENCE VIDEO_CONTENT_SEQ START WITH (select max(id) + 1 from video_content);
CREATE SEQUENCE VIDEO_IMPRESSION_SEQ START WITH (select max(id) + 1 from video_impression);
CREATE SEQUENCE VIDEO_VIEWS_SEQ START WITH (select max(id) + 1 from video_views);