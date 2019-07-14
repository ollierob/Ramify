import * as React from "react";
import "./ImageGallery.css";
import {Magnifier} from "react-image-magnifiers";

export type Image = {
    src: string;
    thumbnailSrc?: string;
}

type Props = {
    images: ReadonlyArray<Image>
}

type State = {
    selected: Image;
}

export default class ImageGallery extends React.PureComponent<Props, State> {

    private doSelect = (image: Image) => this.setState({selected: image});

    constructor(props) {
        super(props);
        this.state = {
            selected: props.images.length ? props.images[0] : null
        };
    }


    render() {

        return <div className="imageGallery">

            <div className="thumbnails">
                <Thumbnails
                    select={this.doSelect}
                    selected={this.state.selected}
                    images={this.props.images}/>
            </div>

            <div className="image">
                {this.state.selected && <Magnifier
                    imageSrc={this.state.selected.src}/>}
            </div>

        </div>;

    }

}

const Thumbnails = (props: {images: ReadonlyArray<Image>, selected: Image, select: (image: Image) => void}) => {
    return <>
        {props.images.map(image => <Thumbnail
            image={image}
            selected={image == props.selected}
            select={props.select}/>)}
    </>;
};

const Thumbnail = (props: {image: Image, selected: boolean, select: (image: Image) => void}) => {
    return <div className="thumbnail">
        <a onClick={() => props.select(props.image)}>
            <img src={props.image.thumbnailSrc || props.image.src}/>
        </a>
    </div>;
};