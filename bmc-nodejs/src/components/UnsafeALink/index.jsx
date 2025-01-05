export default function UnsafeALink({ href, style, children }) {
    return (
        <a href={href} rel='noopener noreferrer' style={style} target='_blank'>
            {children}
        </a>
    );
}
