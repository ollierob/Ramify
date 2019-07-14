declare module 'react-image-magnifiers' {

    import {CSSProperties} from "react";
    type MagnifierProps = {
        className?: string;
        style?: CSSProperties;
        imageSrc: string;
        imageAlt?: string;
        largeImageSrc?: string;
        dragToMove?: boolean;
    }

    export class Magnifier extends React.Component<MagnifierProps> {
    }

}