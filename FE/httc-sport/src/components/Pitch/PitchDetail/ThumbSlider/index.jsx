import { sanImages } from '../dataImageThumbs.js';
import ThumbS from './ThumbS.jsx';

import 'swiper/css';
import 'swiper/css/navigation';
import 'swiper/css/thumbs';

export default function ThumbSlider() {
    return (
        <div>
            <div
                style={{
                    width: 'auto',
                    backgroundColor: '#fff',
                    padding: '20px',
                    borderRadius: '5px',
                }}
            >
                <ThumbS images={sanImages} />
            </div>
        </div>
    );
}
