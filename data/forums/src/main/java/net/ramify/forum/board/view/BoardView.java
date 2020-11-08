package net.ramify.forum.board.view;

import com.google.common.collect.Lists;
import net.ramify.forum.proto.ViewProto;
import net.ramify.forum.topic.view.TopicSummary;

import javax.annotation.Nonnull;
import java.util.List;

public interface BoardView extends BoardMeta {

    @Nonnull
    List<TopicSummary> topics();

    @Nonnull
    @Override
    default ViewProto.BoardView.Builder toProtoBuilder() {
        return BoardMeta.super.toProtoBuilder()
                .addAllTopic(Lists.transform(this.topics(), TopicSummary::toProto));
    }

}
