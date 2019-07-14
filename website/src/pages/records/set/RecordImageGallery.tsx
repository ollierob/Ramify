import * as React from "react";
import {DEFAULT_RECORD_LOADER} from "../../../components/records/RecordLoader";
import {RecordImageList, RecordSet} from "../../../protobuf/generated/record_pb";
import {AsyncData, asyncLoadData} from "../../../components/fetch/AsyncData";
import {RecordSetId} from "../../../components/records/RecordSet";
import {Loading} from "../../../components/style/Loading";
import {NoData} from "../../../components/style/NoData";

type Props = {
    recordSet: RecordSet.AsObject;
}

type State = {
    images: AsyncData<RecordImageList.AsObject>;
}

export default class RecordImageGallery extends React.PureComponent<Props, State> {

    private readonly recordLoader = DEFAULT_RECORD_LOADER;

    constructor(props) {
        super(props);
        this.state = {
            images: {loading: true}
        };
    }

    render() {

        if (this.state.images.loading) return <Loading/>;
        if (!this.state.images.data) return <NoData/>;

        return <>

            {this.state.images.data.imageList.map(i => i.id).join(",")}

        </>;

    }

    componentDidMount() {
        if (this.props.recordSet)
            this.loadImages(this.props.recordSet.id);
    }

    componentDidUpdate(prevProps: Readonly<Props>) {
        if (this.props.recordSet != prevProps.recordSet && this.props.recordSet)
            this.loadImages(this.props.recordSet.id);
    }

    private loadImages(id: RecordSetId) {
        if (!id) return;
        asyncLoadData(id, this.recordLoader.loadRecordImages, images => this.setState({images}));
    }

}