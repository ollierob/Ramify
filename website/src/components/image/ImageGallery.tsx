import * as React from "react";
import {MouseEvent} from "react";
import "./ImageGallery.css";
import {Modal} from "antd";

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

type MagnifierProps = {
    image: Image
    visible: boolean
}

class Magnifier extends React.Component<MagnifierProps, {mouseX?: number, mouseY?: number, magnified?: boolean}> {

    private onMouseMove = (e: MouseEvent<HTMLDivElement>) => this.setState({mouseX: e.pageX, mouseY: e.pageY});
    private onZoomIn = () => this.setState({magnified: true});

    private mouseDown: Date;

    constructor(props) {
        super(props);
        this.state = {};
        this.onMagnifiedMouseDown = this.onMagnifiedMouseDown.bind(this);
        this.onMagnifiedMouseUp = this.onMagnifiedMouseUp.bind(this);
    }

    render() {

        const image = this.props.image;
        if (!image) return null;

        const {magnified} = this.state;

        //TODO adapt https://github.com/AdamRisberg/react-image-magnifiers/blob/master/src/MagnifierRenderer.js

        return <div className="magnifier" onMouseMove={this.onMouseMove}>

            {!magnified && <img
                src={image.src}
                className="base"
                onClick={this.onZoomIn}/>}

            {magnified && <div className="magnified">
                <img
                    src={image.src}
                    className="magnified"
                    style={{transform: "translate(" + this.translateX() + "px, " + this.translateY() + "px"}}
                    onMouseDown={this.onMagnifiedMouseDown}
                    onMouseUp={this.onMagnifiedMouseUp}/>
            </div>}

        </div>;

    }

    private translateX(): number {
        return this.state.mouseX - 200; //FIXME
    }

    private translateY(): number {
        return this.state.mouseY - 200;
    }

    private onMagnifiedMouseDown() {
        this.mouseDown = new Date();
    }

    private onMagnifiedMouseUp() {
        if (!this.mouseDown) return this.zoomOut();
        const msMouseDown = new Date().getTime() - this.mouseDown.getTime();
        if (msMouseDown < 250) return this.zoomOut();
        //Pan
    }

    private zoomOut() {
        this.mouseDown = null;
        this.setState({magnified: false});
    }

    componentDidUpdate(prevProps: Readonly<MagnifierProps>) {
        if (this.props.image != prevProps.image || (!this.props.visible && this.state.magnified))
            this.setState({magnified: false});
    }

}