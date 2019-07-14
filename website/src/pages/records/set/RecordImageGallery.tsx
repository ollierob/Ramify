import * as React from "react";
import {DEFAULT_RECORD_LOADER} from "../../../components/records/RecordLoader";
import {RecordImage, RecordImageList, RecordSet} from "../../../protobuf/generated/record_pb";
import {AsyncData, asyncLoadData} from "../../../components/fetch/AsyncData";
import {RecordSetId} from "../../../components/records/RecordSet";
import {Loading} from "../../../components/style/Loading";
import {NoData} from "../../../components/style/NoData";
import ImageGallery, {ReactImageGalleryItem} from 'react-image-gallery';
import "react-image-gallery/styles/css/image-gallery.css";

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

        const data = this.state.images.data;
        if (!data) return <NoData/>;

        return <>

            <ImageGallery
                thumbnailPosition="top"
                items={data.imageList.map(i => toImage(data.basepath, i))}/>

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

function toImage(basePath: string, image: RecordImage.AsObject): ReactImageGalleryItem {
    return {
        original: basePath + "/" + image.filename,
        thumbnail: basePath + "/" + (image.thumbnail || image.filename)
    };
}