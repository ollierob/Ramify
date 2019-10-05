import * as React from "react";
import {CSSProperties, MouseEvent, RefObject} from "react";
import {HasClass} from "../../style/HasClass";

type Props = {
    children: React.ReactNode;
}

type State = {
    mouseDown?: boolean;
    dragging?: boolean;
    left: number;
    top: number;
    prevX?: number;
    prevY?: number;
}

export default class Draggable extends React.PureComponent<Props, State> {

    private readonly div: RefObject<HTMLDivElement>;

    constructor(props) {
        super(props);
        this.state = {left: 0, top: 0};
        this.div = React.createRef();
        this.onMouseUp = this.onMouseUp.bind(this);
        this.onMouseMove = this.onMouseMove.bind(this);
        this.onMouseDown = this.onMouseDown.bind(this);
        this.preventDefault = this.preventDefault.bind(this);
    }

    render() {
        return <div
            ref={this.div}
            className="drag"
            style={this.state.mouseDown ? DraggingStyle : DraggableStyle}
            onMouseUp={this.onMouseUp}
            onMouseMove={this.onMouseMove}
            onMouseDown={this.onMouseDown}
            onDragStart={this.preventDefault}>

            <div style={{...AbsoluteStyle, left: this.state.left, top: this.state.top}}>
                {this.props.children}
            </div>

        </div>;
    }

    componentDidMount() {
        this.div.current.addEventListener("selectstart", this.preventDefault);
    }

    private preventDefault(e: Event | MouseEvent) {
        e.preventDefault();
    }

    private onMouseDown(e: MouseEvent) {
        this.setState({mouseDown: true, dragging: false, prevX: e.pageX, prevY: e.pageY});
    }

    private onMouseMove(e: MouseEvent) {
        let state = this.state;
        if (!state.mouseDown) return;
        if (!state.dragging) {
            this.setState({dragging: true});
            return;
        }
        const dx = e.pageX - state.prevX;
        const dy = e.pageY - state.prevY;
        console.log("left " + state.left + " => " + e.pageX + " - " + state.prevX + " => " + (state.left + dx));
        this.setState({
            prevX: e.pageX,
            prevY: e.pageY,
            left: state.left + dx,
            top: state.top + dy
        });
    }

    private onMouseUp() {
        this.setState({mouseDown: false, dragging: false});
    }

    componentWillUnmount() {
        this.div.current.removeEventListener("selectstart", this.preventDefault);
    }

}

const FullSizeStyle: CSSProperties = {width: "100%", height: "100%"};
const AbsoluteStyle: CSSProperties = {...FullSizeStyle, position: "absolute"};
const DraggableStyle: CSSProperties = {...FullSizeStyle, cursor: "grab"};
const DraggingStyle: CSSProperties = {...FullSizeStyle, cursor: "grabbing"};