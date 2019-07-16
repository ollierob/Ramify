import * as React from "react";
import "./ImageGallery.css";
import {Card, Modal} from "antd";
import {Magnifier} from "./ImageMagnifier";
import {stringMultimap} from "../Maps";

export type Image = {
    src: string;
    thumbnailSrc?: string;
    alt?: string;
    group: string;
}

type Props = {
    images: ReadonlyArray<Image>
}

type State = {
    selected: Image;
    modal?: boolean;
    imageGroups: ImageGroups;
}

export default class ImageGallery extends React.PureComponent<Props, State> {

    private doSelect = (image: Image) => this.setState({selected: image, modal: true});
    private doClose = () => this.setState({modal: false});

    constructor(props) {
        super(props);
        this.state = {
            selected: props.images.length ? props.images[0] : null,
            imageGroups: buildImageGroups(props.images)
        };
    }


    render() {

        return <div className="imageGallery">

            <Thumbnails
                select={this.doSelect}
                selected={this.state.selected}
                images={this.state.imageGroups}
                mode="blocks"/>

            <ImageModal
                visible={this.state.modal}
                images={this.state.imageGroups}
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

const Thumbnails = (props: {images: ImageGroups, mode: "blocks" | "inline"} & SelectorProps) => {
    const groups = Object.keys(props.images);
    if (!groups.length) return null;
    const firstGroup = groups[0];
    const useCards = props.mode == "blocks" && (groups.length > 1 || !!firstGroup);
    return <div className={"thumbnails " + props.mode}>
        {useCards && Object.keys(props.images).map(group => {
            const images = props.images[group];
            return <Card className="card" title={group || "Thumbnails"} size="small">
                {images.map(image => <Thumbnail {...props} image={image}/>)}
            </Card>;
        })}
        {!useCards && <>
            {props.images[firstGroup].map(image => <Thumbnail {...props} image={image}/>)}
        </>}
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

const ImageModal = (props: {visible: boolean, images: ImageGroups, close: () => void} & SelectorProps) => {

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

type ImageGroups = {[group: string]: ReadonlyArray<Image>}

function buildImageGroups(images: ReadonlyArray<Image>): ImageGroups {
    return stringMultimap(images, image => image.group);
}