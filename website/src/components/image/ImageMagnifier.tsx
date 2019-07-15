import * as React from "react";
import {MouseEvent} from "react";
import {Image} from "./ImageGallery";

type Props = {
    image: Image
    visible: boolean
}

type State = {
    mouseX?: number;
    mouseY?: number;
    magnified?: boolean;
    dragStart?: Date;
}

export class Magnifier extends React.Component<Props, State> {

    private onMouseMove = (e: MouseEvent<HTMLDivElement>) => this.setState({mouseX: e.pageX, mouseY: e.pageY});

    constructor(props) {
        super(props);
        this.state = {};
        this.zoomIn = this.zoomIn.bind(this);
        this.onMagnifiedMouseDown = this.onMagnifiedMouseDown.bind(this);
        this.onMagnifiedMouseUp = this.onMagnifiedMouseUp.bind(this);
    }

    render() {

        const image = this.props.image;
        if (!image) return null;

        const {magnified} = this.state;

        //TODO adapt https://github.com/AdamRisberg/react-image-magnifiers/blob/master/src/MagnifierRenderer.js
        //TODO or use https://github.com/ajainarayanan/react-pan-zoom

        return <div className="magnifier" onMouseMove={this.onMouseMove}>

            {!magnified && <img
                src={image.src}
                className="base"
                onClick={this.zoomIn}/>}

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

    private zoomIn() {
        this.setState({magnified: true, dragStart: null});
    }

    private onMagnifiedMouseDown() {
        this.setState({dragStart: new Date()});
    }

    private onMagnifiedMouseUp() {
        const {dragStart} = this.state;
        if (!dragStart) return this.zoomOut();
        const dragTime = new Date().getTime() - dragStart.getTime();
        if (dragTime < 500) return this.zoomOut();
        //Pan
    }

    private zoomOut() {
        this.setState({magnified: false, dragStart: null});
    }

    componentDidUpdate(prevProps: Readonly<Props>) {
        if (this.props.image != prevProps.image || (!this.props.visible && this.state.magnified))
            this.zoomOut();
    }

}