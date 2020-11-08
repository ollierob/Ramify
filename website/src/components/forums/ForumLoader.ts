import {BoardId} from "./Board";
import {BoardView, ForumView, TopicView} from "../../protobuf/generated/forum_view_pb";
import {TopicId} from "./Topic";
import {protoGet} from "../fetch/ProtoFetch";

export interface ForumLoader {

    loadForum(): Promise<ForumView.AsObject>

    loadBoard(id: BoardId): Promise<BoardView.AsObject>

    loadTopic(id: TopicId): Promise<TopicView.AsObject>

}

class ProtoForumLoader implements ForumLoader {

    loadForum() {
        return protoGet("/community/forums", ForumView.deserializeBinary)
            .then(f => f?.toObject());
    }

    loadBoard(id: BoardId) {
        return Promise.resolve(undefined);
    }

    loadTopic(id: TopicId) {
        return Promise.resolve(undefined);
    }

}