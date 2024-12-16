import React, { useState } from "react";
import axios from "axios";

const ImageGallery = () => {
  const [image, setImage] = useState(null);

  const fetchImage = async () => {
    try {
      const response = await axios.get("http://localhost:8080/images", {
        responseType: "blob",
      });
      const imageUrl = URL.createObjectURL(response.data);
      setImage(imageUrl);
    } catch (error) {
      console.error("Error fetching image:", error);
    }
  };

  return (
    <div style={{ textAlign: "center", marginTop: "50px" }}>
      <button onClick={fetchImage} style={{ padding: "10px 20px", fontSize: "16px" }}>
        Show Next Image
      </button>
      {image && <div><img src={image} alt="Current" style={{ marginTop: "20px", maxWidth: "100%" }} /></div>}
    </div>
  );
};

export default ImageGallery;
