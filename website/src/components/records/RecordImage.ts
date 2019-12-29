type RecordImageLike = {
    recordsetid?: string;
    imageid?: string;
}

export function recordImageHref(record: RecordImageLike): string {
    if (!record || !record.imageid) return null;
    return "/records/images/set/" + record.recordsetid + "/" + record.imageid;
}