import * as React from "react";
import "./ImageGallery.css";
import {Card, Modal} from "antd";
import {Magnifier} from "./ImageMagnifier";

export type Image = {
    src: string;
    thumbnailSrc?: string;
    alt?: string;
    group: string;
}

type Props = {
    images: ReadonlyArray<Image>
    showGroups?: boolean;
}

type State = {
    selected: Image;
    modal?: boolean;
    imageGroups: ImageGroups;
}

export default class ImageGallery extends React.PureComponent<Props, State> {

    private doSelect = (image: Image) => this.setState({selected: image, modal: true});
    private doClose = () => this.setState({modal: false});

    constructor(props: Props) {
        super(props);
        this.state = {
            selected: props.images.length ? props.images[0] : null,
            imageGroups: buildImageGroups(props.images, props.showGroups)
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

    componentDidUpdate(prevProps: Readonly<Props>) {
        if (this.props.images != prevProps.images)
            this.setState({imageGroups: buildImageGroups(this.props.images, this.props.showGroups)});
    }

}

type SelectorProps = {
    selected: Image;
    select: (image: Image) => void;
}

const Thumbnails = (props: {images: ImageGroups, mode: "blocks" | "inline"} & SelectorProps) => {

    const groups = Object.keys(props.images).sort();
    if (!groups.length) return null;
    const firstGroup = groups[0];
    const useCards = props.mode == "blocks" && (groups.length > 1 || !!firstGroup);

    return <div className={"thumbnails " + props.mode}>

        {useCards && groups.map(groupId => {
            const group = props.images[groupId];
            return <Card className="card" title={group.title} size="small">
                {group.images.map(image => <Thumbnail {...props} image={image}/>)}
            </Card>;
        })}

        {!useCards && <>
            {props.images[firstGroup].images.map(image => <Thumbnail {...props} image={image}/>)}
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

type ImageGroups = {[group: string]: {title: string, images: Image[]}}

function buildImageGroups(images: ReadonlyArray<Image>, showGroups: boolean): ImageGroups {
    const i: ImageGroups = {};
    images.forEach(image => {
        const groupId = showGroups ? image.group : "";
        let g = i[groupId];
        if (!g) {
            g = {title: showGroups ? image.group : "Thumbnails", images: []};
            i[groupId] = g;
        }
        g.images.push(image);
    });
    return i;
}