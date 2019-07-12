import {RecordSet, RecordSetList} from "../../protobuf/generated/record_pb";
import {PlaceId} from "../places/Place";
import {queryParameters} from "../fetch/Fetch";
import {protoFetch} from "../fetch/ProtoFetch";

export interface RecordLoader {

    loadRecordSets(options?: RecordSetOptions): Promise<ReadonlyArray<RecordSet.AsObject>>

}

type RecordSetOptions = {place?: PlaceId, limit?: number}

class ProtoRecordLoader implements RecordLoader {

    loadRecordSets(options) {
        const url = "/records/sets" + queryParameters(options);
        return protoFetch(url, RecordSetList.deserializeBinary)
            .then(l => l ? l.getRecordsetList().map(l => l.toObject()) : []);
    }

}

export const DEFAULT_RECORD_LOADER: RecordLoader = new ProtoRecordLoader();