import * as React from "react";
import "./ImageGallery.css";
import {Modal} from "antd";
import {Magnifier} from "./ImageMagnifier";

export type Image = {
    src: string;
    thumbnailSrc?: string;
    alt?: string;
}

type Props = {
    images: ReadonlyArray<Image>
}

type State = {
    selected: Image;
    modal?: boolean;
}

export default class ImageGallery extends React.PureComponent<Props, State> {

    private doSelect = (image: Image) => this.setState({selected: image, modal: true});
    private doClose = () => this.setState({modal: false});

    constructor(props) {
        super(props);
        this.state = {
            selected: props.images.length ? props.images[0] : null
        };
    }


    render() {

        return <div className="imageGallery">

            <Thumbnails
                select={this.doSelect}
                selected={this.state.selected}
                images={this.props.images}
                mode="blocks"/>

            <ImageModal
                visible={this.state.modal}
                images={this.props.images}
                close={this.doClose}
                selected={this.state.selected}
                select={this.doSelect}/>

        </div>;

    }

}

type SelectorProps = {
    selected: Image;
    select: (image: Image) => void;
}

const Thumbnails = (props: {images: ReadonlyArray<Image>, mode: "blocks" | "inline"} & SelectorProps) => {
    return <div className={"thumbnails " + props.mode}>
        {props.images.map(image => <Thumbnail {...props} image={image}/>)}
    </div>;
};

const Thumbnail = (props: {image: Image} & SelectorProps) => {
    const selected = props.selected == props.image;
    return <div className={"thumbnail" + (selected ? " selected" : "")}>
        <a onClick={() => props.select(props.image)}>
            <img src={props.image.thumbnailSrc || props.image.src} alt={props.image.alt || "Thumbnail"}/>
        </a>
    </div>;
};

const ImageModal = (props: {visible: boolean, images: ReadonlyArray<Image>, close: () => void} & SelectorProps) => {

    return <Modal
        {...props}
        className="imageGallery"
        footer={null}
        onCancel={props.close}
        width="90%">

        <Thumbnails
            {...props}
            mode="inline"/>

        <Magnifier
            {...props}
            image={props.selected}/>

    </Modal>;

};

