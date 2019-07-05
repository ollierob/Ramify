import * as React from "react";
import {Link} from "../../protobuf/generated/link_pb";
import TypeMap = Link.Type;

export const PlaceLinks = (props: {links: ReadonlyArray<Link.AsObject>}) => {
    const links = props.links;
    if (!links || !links.length) return null;
    return <div className="links">
        {links.map(link => <PlaceLink link={link}/>)}
    </div>
};

const PlaceLink = (props: {link: Link.AsObject}) => {
    const link = props.link;
    if (!link) return null;
    if (link.type == TypeMap.WIKIPEDIA) return <a href={"https://" + link.href} target="_blank"><img src="/images/wikipedia.png"/></a>
    return null;
};